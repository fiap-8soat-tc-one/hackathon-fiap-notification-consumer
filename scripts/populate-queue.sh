#!/bin/bash

QUEUE_URL=http://localhost:4566/000000000000/notification-event-queue
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=us-east-1
aws --endpoint-url=http://localhost:4566 sqs send-message \
    --queue-url $QUEUE_URL \
    --message-body '{
        "id": "e-mail/test%40test.com/teste_s3_mimo.mp4",
        "status": "PROCESSED"
    }'
