#  Copyright 2016 The TensorFlow Authors. All Rights Reserved.
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
"""An Example of a DNNClassifier for the Iris dataset."""
from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import argparse
import tensorflow as tf
import pandas as pd

parser = argparse.ArgumentParser()
parser.add_argument('--batch_size', default=100, type=int, help='batch size')
parser.add_argument('--train_steps', default=2000, type=int,
                    help='number of training steps')


CSV_COLUMN_NAMES = ['age', 'gender', 'income', 'family_holiday_maker', 'foodie',
                    'backpacker', 'history_buff', 'nightlife_seeker', 'eco_tourist', 'trendsetter',
                    'nature_lover', 'urban_explorer', 'thrill_seeker', 'beach_goer', 'sixty_traveller',
                    'like_a_local', 'luxury_traveller', 'vegetarian', 'shopping_fanatic', 'thrifty_traveller',
                    'art_and_architecture_lover', 'peace_and_quiet_seeker']
LIKE = ['No', 'Yes']


def load_data(y_name='family_holiday_maker'):
    """Returns the iris dataset as (train_x, train_y), (test_x, test_y)."""
    train_path = './data/preference_train.csv'
    test_path = './data/preference_test.csv'

    train = pd.read_csv(train_path, names=CSV_COLUMN_NAMES, header=0)
    # train_x, train_y = train, train.pop(y_name)
    train_x, train_y = train.iloc[:, :3], train.pop(y_name)

    test = pd.read_csv(test_path, names=CSV_COLUMN_NAMES, header=0)
    # test_x, test_y = test, test.pop(y_name)
    test_x, test_y = test.iloc[:, :3], test.pop(y_name)

    return (train_x, train_y), (test_x, test_y)


def train_input_fn(features, labels, batch_size):
    """An input function for training"""
    # Convert the inputs to a Dataset.
    dataset = tf.data.Dataset.from_tensor_slices((dict(features), labels))

    # Shuffle, repeat, and batch the examples.
    dataset = dataset.shuffle(1000).repeat().batch(batch_size)

    # Return the read end of the pipeline.
    return dataset.make_one_shot_iterator().get_next()


def eval_input_fn(features, labels, batch_size):
    """An input function for evaluation or prediction"""
    features=dict(features)
    if labels is None:
        # No labels, use only features.
        inputs = features
    else:
        inputs = (features, labels)

    # Convert the inputs to a Dataset.
    dataset = tf.data.Dataset.from_tensor_slices(inputs)

    # Batch the examples
    assert batch_size is not None, "batch_size must not be None"
    dataset = dataset.batch(batch_size)

    # Return the read end of the pipeline.
    return dataset.make_one_shot_iterator().get_next()


# The remainder of this file contains a simple example of a csv parser,
#     implemented using a the `Dataset` class.

# `tf.parse_csv` sets the types of the outputs to match the examples given in
#     the `record_defaults` argument.
CSV_TYPES = [[0], [0], [0], [0], [0], [0], [0], [0], [0], [0],
                        [0], [0], [0], [0], [0], [0], [0], [0], [0], [0], [0], [0]]

def _parse_line(line):
    # Decode the line into its fields
    fields = tf.decode_csv(line, record_defaults=CSV_TYPES)

    # Pack the result into a dictionary
    features = dict(zip(CSV_COLUMN_NAMES, fields))


    # Separate the label from the features
    label = features.pop('family_holiday_maker')

    return features, label


def csv_input_fn(csv_path, batch_size):
    # Create a dataset containing the text lines.
    dataset = tf.data.TextLineDataset(csv_path).skip(1)

    # Parse each line.
    dataset = dataset.map(_parse_line)

    # Shuffle, repeat, and batch the examples.
    dataset = dataset.shuffle(1000).repeat().batch(batch_size)

    # Return the read end of the pipeline.
    return dataset.make_one_shot_iterator().get_next()


def main(argv):
    args = parser.parse_args(argv[1:])

    # Fetch the data
    (train_x, train_y), (test_x, test_y) = load_data()

    # Feature columns describe how to use the input.
    my_feature_columns = []
    for key in train_x.keys():
        my_feature_columns.append(tf.feature_column.numeric_column(key=key))

    age = tf.feature_column.categorical_column_with_identity(key='age', num_buckets=7)

    gender = tf.feature_column.categorical_column_with_identity(key='gender', num_buckets=2)

    income = tf.feature_column.categorical_column_with_identity(key='income', num_buckets=8)

    age_x_gender = tf.feature_column.crossed_column(
            [age, gender], hash_bucket_size=1000)
    age_x_income = tf.feature_column.crossed_column(
        [age, income], hash_bucket_size=1000)
    gender_x_income = tf.feature_column.crossed_column(
        [gender, income], hash_bucket_size=1000)
    age_x_gender_x_income = tf.feature_column.crossed_column(
            [age, gender, income], hash_bucket_size=1000)

    my_feature_columns.append(tf.feature_column.embedding_column(age_x_gender, 1))
    my_feature_columns.append(tf.feature_column.embedding_column(age_x_income, 1))
    my_feature_columns.append(tf.feature_column.embedding_column(gender_x_income, 1))
    my_feature_columns.append(tf.feature_column.embedding_column(age_x_gender_x_income, 1))

    # Build 2 hidden layer DNN with 10, 10 units respectively.
    classifier = tf.estimator.DNNClassifier(
        feature_columns=my_feature_columns,
        # Two hidden layers of 10 nodes each.
        hidden_units=[10, 10],
        # The model must choose between 2 classes.
        n_classes=2)

    # Train the Model.
    classifier.train(
        input_fn=lambda:train_input_fn(train_x, train_y,
                                                 args.batch_size),
        steps=args.train_steps)

    # Evaluate the model.
    eval_result = classifier.evaluate(
        input_fn=lambda:eval_input_fn(test_x, test_y,
                                                args.batch_size))

    print('\nTest set accuracy: {accuracy:0.3f}\n'.format(**eval_result))

    # Generate predictions from the model
    expected = ['Yes', 'No']
    predict_x = {
        'age': [1, 1], 'gender': [0, 1], 'income': [3, 5], 'foodie': [1, 1],
        'backpacker': [0, 0], 'history_buff': [1, 0], 'nightlife_seeker': [1, 0], 'eco_tourist': [0, 0], 'trendsetter': [0, 0],
        'nature_lover': [1, 0], 'urban_explorer': [1, 1], 'thrill_seeker': [1, 0], 'beach_goer': [1, 1], 'sixty_traveller': [0, 0],
        'like_a_local': [0, 0], 'luxury_traveller': [0, 1], 'vegetarian': [0, 0], 'shopping_fanatic': [1, 0], 'thrifty_traveller': [0, 0],
        'art_and_architecture_lover': [0, 1], 'peace_and_quiet_seeker': [0, 0],
    }

    predictions = classifier.predict(
        input_fn=lambda:eval_input_fn(predict_x,
                                                labels=None,
                                                batch_size=args.batch_size))

    for pred_dict, expec in zip(predictions, expected):
        template = ('\nPrediction is "{}" ({:.1f}%), expected "{}"')

        class_id = pred_dict['class_ids'][0]
        probability = pred_dict['probabilities'][class_id]

        print(template.format(LIKE[class_id],
                              100 * probability, expec))


if __name__ == '__main__':
    tf.logging.set_verbosity(tf.logging.INFO)
    tf.app.run(main)