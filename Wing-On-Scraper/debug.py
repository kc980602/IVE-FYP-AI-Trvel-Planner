# -*- coding: utf-8 -*-
from BeautifulSoup import BeautifulSoup
import requests

def extractRawContent(method, uri, payload={}):
    method = method.upper()
    if method == 'GET':
        result = requests.get(uri, data=payload)
    elif method == 'POST':
        result = requests.post(uri, data=payload)
    html = result.content
    return BeautifulSoup(html)

if __name__ == '__main__':
    productId = 7203
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

        print(stayTag.contents[0].strip() if stayTag is not None else 'N/A')
        print(tripDayTag.contents[0])
        print(itinearyTag.contents[0].strip())
        for item in eatsTag:
            print(item.contents[0].strip())
        for item in remarks:
            print()
        print('')
