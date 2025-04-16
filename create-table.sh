#!/bin/bash

export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_DEFAULT_REGION=us-east-1
aws dynamodb create-table \
    --table-name fiap-hackaton-uploads \
    --attribute-definitions AttributeName=id,AttributeType=S \
    AttributeName=email,AttributeType=S \
    AttributeName=status_upload,AttributeType=S \
    AttributeName=url_download,AttributeType=S \
    AttributeName=data_criacao,AttributeType=S \
    --key-schema AttributeName=id,KeyType=HASH \
    --billing-mode PAY_PER_REQUEST \
    --global-secondary-indexes "[{\"IndexName\": \"email-index\", \"KeySchema\": [{\"AttributeName\": \"email\", \"KeyType\": \"HASH\"}], \"Projection\": {\"ProjectionType\": \"ALL\"}}, {\"IndexName\": \"url_download-index\", \"KeySchema\": [{\"AttributeName\": \"url_download\", \"KeyType\": \"HASH\"}], \"Projection\": {\"ProjectionType\": \"ALL\"}}, {\"IndexName\": \"status-index\", \"KeySchema\": [{\"AttributeName\": \"status_upload\", \"KeyType\": \"HASH\"}], \"Projection\": {\"ProjectionType\": \"ALL\"}}, {\"IndexName\": \"data-criacao-index\", \"KeySchema\": [{\"AttributeName\": \"data_criacao\", \"KeyType\": \"HASH\"}], \"Projection\": {\"ProjectionType\": \"ALL\"}}]" \
    --endpoint-url=http://localhost:4566
