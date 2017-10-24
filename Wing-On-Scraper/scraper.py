# -*- coding: utf-8 -*-
import requests
import re
import time

from BeautifulSoup import BeautifulSoup

def extractRawContent(method, uri, payload={}):
    method = method.upper()
    if method == 'GET':
        result = requests.get(uri, data=payload)
    elif method == 'POST':
        result = requests.post(uri, data=payload)
    html = result.content
    return BeautifulSoup(html)

def fetchCategory():
    category = {}
    html = extractRawContent('GET', 'https://tours.wingontravel.com/')
    categoryParent = html.find('div', attrs={'class': 'category_leaves'})
    categoryTag = categoryParent.findAll('dl')
    categoryTag = categoryTag if categoryTag is not None else []
    for item in categoryTag:
        title = item.find('dt').find('a').get('title')
        uri = item.find('dt').find('a').get('href')
        category[title] = uri
    return category

def fetchTrip(productId):
    itineary = []
    remarkList = []
    eats = []
    html = extractRawContent('POST', 'https://tours.wingontravel.com/delay/detailtourroutesegments', {'productID': productId})
    parentTag = html.findAll('dl')
    parentTag = parentTag if parentTag is not None else []
    for route in parentTag:
        tripDayTag = route.find('div', attrs={'class': 'segment_day'})
        itinearyTag = route.find('div', attrs={'class': 'segment_title'})
        remarkTag = route.find('div' ,attrs={'class': 'segment_content'})
        stayTag = route.find('li', attrs={'class': 'stay'})
        eatsTag = remarkTag.findAll('li', attrs={'class': 'eat'})
        remarks = remarkTag.findAll('p')
        remarks = remarks if remarks is not None else []

        stayAt = stayTag.contents[0].strip() if stayTag is not None else 'N/A'
        tripDay = tripDayTag.contents[0]
        desc = itinearyTag.contents[0].strip()
        for item in eatsTag:
            eats.append(item.contents[0].strip())
        for item in remarks:
            remarkList.append(item.contents[0].strip())
        itineary.append({'day': tripDay, 'intineary': desc, 'eat': eats, 'remark': remarkList, 'created_at': time.time(), 'updated_at': time.time()})
    return {'productId': productId, 'itineary': itineary}

def extractProductList(uri, product = []):
    detailFormat = 'https:\/\/tours.wingontravel.com\/detail\/.+-\d+-(\d+)'
    items = extractRawContent('GET', value)
    titleList = items.findAll('div', attrs={'class': 'product_list_title'})
    for titleTag in titleList:
        title = titleTag.find('a').get('title')
        tripURI = titleTag.find('a').get('href')
        productID = re.search(detailFormat, tripURI).group(1)
        product.append(fetchTrip(productID))
        print(u'ProductID: {0}, 旅行團: {1}'.format(productID, title))
    paginatorTag = items.find('a', attrs={'class': 'next_page'})
    if paginatorTag:
        pagePath = paginatorTag.get('href')
        print(u'Next Page avaliable. URL: {0}'.format(pagePath))
        return extractProductList(pagePath, product)
    return product

if __name__ == '__main__':
    tours = []
    categories = fetchCategory()
    for key, value in categories.items():
        print(u'Extracting {0} Content'.format(key))
        tours.append(extractProductList(value))
