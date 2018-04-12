"""
Twitter ETL
"""

import sys
import json
import os
from pyspark import SparkContext
from pyspark.streaming import StreamingContext

from twitter import *

import time

BATCH_INTERVAL = 10

def main():
    sc  = SparkContext('local[*]', 'Twitter ETL')
    ssc = StreamingContext(sc, BATCH_INTERVAL)
    rdd = ssc.sparkContext.parallelize([0])
    
    # create an empty DStream
    stream = ssc.queueStream([], default=rdd)
    
    # transform the DStream by retrieving some tweets for each RDD
    stream = stream.transform(lambda t, rdd: rdd.flatMap(lambda x: stream_twitter_data())) \
                .filter(lambda x: x.get('text') is not None) \
                .pprint()
    ssc.start()
    ssc.awaitTermination()

def stream_twitter_data():
    return TwitterStream(auth=OAuth( \
                os.environ['TW_API_KEY'], os.environ['TW_API_SECRET'], \
                os.environ['TW_CONSUMER_KEY'], os.environ['TW_CONSUMER_SECRET'])) \
            .statuses.sample()

if __name__ == '__main__':
    sys.exit(main())