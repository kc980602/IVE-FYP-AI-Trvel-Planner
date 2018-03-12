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
CATEGORICAL_COLUMNS = ['age', 'gender', 'country']
GENDER_CHAR = ['female', 'male']
AGE_CHAR = ['Under 12', '13-17', '18-24', '25-34', '35-49', '50-64', '65+']
# INCOME_CHAR = ['0', '1', '2', '3', '4', '5', '6', '7']
# COUNTRY_CHAR = ['afghanistan', 'albania', 'algeria', 'andorra', 'angola', 'antigua and barbuda', 'argentina',
#                          'armenia', 'aruba', 'australia', 'austria', 'azerbaijan', 'bahamas, the', 'bahrain',
#                          'bangladesh', 'barbados', 'belarus', 'belgium', 'belize', 'benin', 'bhutan', 'bolivia',
#                          'bosnia and herzegovina', 'botswana', 'brazil', 'brunei', 'bulgaria', 'burkina faso', 'burma',
#                          'burundi', 'cabo verde', 'cambodia', 'cameroon', 'canada', 'central african republic', 'chad',
#                          'chile', 'china', 'colombia', 'comoros', 'congo, democratic republic of the congo',
#                          'republic of the costa rica', 'croatia', 'cuba', 'curacao', 'cyprus', 'czechia', 'denmark',
#                          'djibouti', 'dominica', 'dominican republic', 'east timor (see timor-leste)', 'ecuador',
#                          'egypt', 'el salvador', 'equatorial guinea', 'eritrea', 'estonia', 'ethiopia', 'fiji',
#                          'finland', 'france', 'gabon', 'gambia, the', 'georgia', 'germany', 'ghana', 'greece',
#                          'grenada', 'guatemala', 'guinea', 'guinea-bissau', 'guyana', 'haiti', 'holy see', 'honduras',
#                          'hungary', 'iceland', 'india', 'indonesia', 'iran', 'iraq', 'ireland', 'israel', 'italy',
#                          'jamaica', 'japan', 'jordan', 'kazakhstan', 'kenya', 'kiribati', 'korea, north',
#                          'korea, south', 'kosovo', 'kuwait', 'kyrgyzstan', 'laos', 'latvia', 'lebanon', 'lesotho',
#                          'liberia', 'libya', 'liechtenstein', 'lithuania', 'luxembourg', 'macedonia', 'madagascar',
#                          'malawi', 'malaysia', 'maldives', 'mali', 'malta', 'marshall islands', 'mauritania',
#                          'mauritius', 'mexico', 'micronesia', 'moldova', 'monaco', 'mongolia', 'montenegro', 'morocco',
#                          'mozambique', 'namibia', 'nauru', 'nepal', 'netherlands', 'new zealand', 'nicaragua', 'niger',
#                          'nigeria', 'north korea', 'norway', 'oman', 'pakistan', 'palau', 'palestinian territories',
#                          'panama', 'papua new guinea', 'paraguay', 'peru', 'philippines', 'poland', 'portugal', 'qatar',
#                          'romania', 'russia', 'rwanda', 'saint kitts and nevis', 'saint lucia',
#                          'saint vincent and the grenadines', 'samoa', 'san marino', 'sao tome and principe',
#                          'saudi arabia', 'senegal', 'serbia', 'seychelles', 'sierra leone', 'singapore', 'sint maarten',
#                          'slovakia', 'slovenia', 'solomon islands', 'somalia', 'south africa', 'south korea',
#                          'south sudan', 'spain', 'sri lanka', 'sudan', 'suriname', 'swaziland', 'sweden', 'switzerland',
#                          'syria', 'tajikistan', 'tanzania', 'thailand', 'timor-leste', 'togo', 'tonga',
#                          'trinidad and tobago', 'tunisia', 'turkey', 'turkmenistan', 'tuvalu', 'uganda', 'ukraine',
#                          'united arab emirates', 'united kingdom', 'uruguay', 'uzbekistan', 'vanuatu', 'venezuela',
#                          'vietnam', 'yemen', 'zambia', 'zimbabwe']

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
    X = dataset.iloc[:, 1:3].values
    y = dataset.iloc[:, 4:].values

    # encode age
    X[:, 0] = LabelEncoder().fit_transform(X[:, 0])
    # encode gender
    X[:, 1] = LabelEncoder().fit_transform(X[:, 1])
    # encode country
    # X[:, 2] = LabelEncoder().fit_transform(X[:, 2])

    # dummy variables (binary one-hot encoding)
    enc = hot_encode(X)
    X = enc.transform(X).toarray()

    return X,y,enc

def training():
    dataset = readfile('users1.csv')
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
    model.add(Dense(units=11, kernel_initializer='uniform', activation='relu', input_shape=(8,)))
    model.add(Dropout(rate=0.1))
    model.add(Dense(units=11, kernel_initializer='uniform', activation='relu'))
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

    model.save('users.model')

def predict(gender, age):
    dataset = readfile('users1.csv')
    X, y, enc = preprocess(dataset)
    data = enc.transform(np.array([[age, gender]])).toarray()

    model = load_model('users.model')
    print ("\n----------------")
    print ("NEW OBSERVATION:")
    print ("----------------")
    print ("Gender: {gender}\nAge Group: {age}".format(gender=GENDER_CHAR[gender], age=AGE_CHAR[age]))
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
    if not Path('users.model').exists():
        training()
    predict(FEMALE, ABOVE_18)