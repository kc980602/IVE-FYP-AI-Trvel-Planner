import numpy as np
import pandas as pd
import json
import keras
from pathlib import Path
from sklearn.metrics import confusion_matrix
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder, OneHotEncoder
from sklearn.preprocessing import StandardScaler
from keras.models import load_model
from keras.models import Sequential # used to initialize neural network
from keras.layers import Dense # used to create layers
from keras.layers import Dropout # used for dropout regularization
from keras.wrappers.scikit_learn import KerasClassifier
from sklearn.model_selection import GridSearchCV
# Testing
from itertools import product

AVAILABLE_COLUMN = [
    'family_holiday_maker','foodie','backpacker','history_buff',
    'nightlife_seeker','eco_tourist','trendsetter','nature_lover',
    'urban_explorer','thrill_seeker','beach_goer','60+_traveller',
    'like_a_local','luxury_traveller','vegetarian','shopping_fanatic',
    'thrifty_traveller','art_and_architecture_lover','peace_and_quiet_seeker'
]
CATEGORICAL_COLUMNS = ['age', 'gender', 'income']
GENDER_CHAR = ['Female', 'Male']
AGE_CHAR = ['Under 12', '12-17', '18-24', '25-34', '35-49', '50-64', '65+']
INCOME_CHAR = ['0', '1', '2', '3', '4', '5', '6', '7']

FEMALE = 0
MALE = 1
UNDER_12 = 0
ABOVE_12 = 1
ABOVE_18 = 2
ABOVE_25 = 3
ABOVE_35 = 4
ABOVE_50 = 5
ABOVE_65 = 6

# read dataset
def readfile(filename):
    dataset = pd.read_csv(filename)

    return dataset

def hot_encode(X):
    return OneHotEncoder().fit(X)

def preprocess(dataset):
    X = dataset.iloc[:, 1:4].values
    y = dataset.iloc[:, 4:].values

    # encode age
    X[:, 0] = LabelEncoder().fit_transform(X[:, 0])
    # encode gender
    X[:, 1] = LabelEncoder().fit_transform(X[:, 1])
    # encode income
    X[:, 2] = LabelEncoder().fit_transform(X[:, 2])

    # dummy variables (binary one-hot encoding)
    enc = hot_encode(X)
    X = enc.transform(X).toarray()

    return X,y,enc

def training():
    dataset = readfile('preference.csv')
    X, y, enc = preprocess(dataset)
    # split dataset into training set and test set
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=0)

    # Scale features (standardize range of independent variables)
    sc_X = StandardScaler()
    X_train = sc_X.fit_transform(X_train)
    X_test = sc_X.transform(X_test)

    # build artificial neural network
    # initialize neural network
    model = Sequential()
    model.add(Dense(units=18, kernel_initializer='uniform', activation='relu', input_shape=(17,)))
    model.add(Dropout(rate=0.1))
    model.add(Dense(units=18, kernel_initializer='uniform', activation='relu'))
    model.add(Dropout(rate=0.1))
    model.add(Dense(units=19, kernel_initializer='uniform', activation='sigmoid'))
    model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

    # fit neural network to training set
    model.fit(X_train, y_train, batch_size=1024, epochs=100)
    print ("Training complete!")

    # predict test set results
    y_pred = model.predict(X_test)
    y_pred = (y_pred > 0.5)
    print ("Testing complete!")

    model.save('preference.model')

def predict(gender, age, income):
    dataset = readfile('preference.csv')
    X, y, enc = preprocess(dataset)
    data = enc.transform(np.array([[gender, age, income]])).toarray()

    model = load_model('preference.model')
    print ("\n----------------")
    print ("NEW OBSERVATION:")
    print ("----------------")
    print ("Gender: {gender}\nAge Group: {age}\nIncome: {income}".format(gender=GENDER_CHAR[gender], age=AGE_CHAR[age], income=INCOME_CHAR[income]))
    # preprocessing new observation
    # scale new observation
    sc_X = StandardScaler()
    X = sc_X.fit_transform(X)
    data = sc_X.transform(data)
    prediction_prob = model.predict(data)
    resp = {}
    for idx, value in np.ndenumerate(prediction_prob[0]):
        tag = AVAILABLE_COLUMN[idx[0]]
        resp[tag] = value
        print("Tags: {tag}, Result: {result}, Prob: {prob}".format(tag=tag, result=("Yes" if value > 0.5 else "No"), prob=value))
    sorted(resp.values())
    print(resp)


if __name__ == '__main__':
    if not Path('preference.model').exists():
        training()
    predict(FEMALE, UNDER_12, 7)