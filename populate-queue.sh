#!/bin/bash

QUEUE_URL=http://localhost:4566/000000000000/email-queue

aws --endpoint-url=http://localhost:4566 sqs send-message \
    --queue-url $QUEUE_URL \
    --message-body '{
        "to": "test@example.com",
        "subject": "Bem-vindo!",
        "body": "Olá! Esta é uma notificação enviada localmente via SQS."
    }'
