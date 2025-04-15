#!/bin/bash

export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=us-east-1
aws --endpoint-url=http://localhost:4566 dynamodb put-item \
    --table-name fiap-hackaton-uploads \
    --item '{
        "id": {"S": "e-mail/test%40test.com/teste_s3_mimo.mp4"},
        "email": {"S": "caio.isikawa@gmail.com"},
        "status_upload": {"S": "PROCESSED"},
        "url_download": {"S": "https://s3.amazonaws.com/bucket-fiap-hackaton-g32-files/teste_s3_mimo.mp4"},
        "data_criacao": {"S": "2023-10-01T12:00:00Z"}
    }'