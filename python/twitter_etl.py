"""
Twitter ETL
"""

import os
from twitter import Twitter, OAuth, TwitterHTTPError, TwitterStream

def get_and_process_tweets(oauth, batch_count=10):

    print("Starting Twitter Batch Processing")

    # Get twitter stream
    twitter_stream = TwitterStream(auth=oauth)
    iterator = twitter_stream.statuses.sample()
    for tweet in iterator:
        print(tweet)

if __name__ == "__main__":
    NUM_BATCHES = 5
    oauth_t = OAuth(os.environ['TW_API_KEY'], os.environ['TW_API_SECRET'], \
                os.environ['TW_CONSUMER_KEY'], os.environ['TW_CONSUMER_SECRET'])

    for k in range(NUM_BATCHES):
        get_and_process_tweets(oauth_t, 200)
