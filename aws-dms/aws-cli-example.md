# AWS DMS CLI example (replace placeholders before running)

# 1. Create source endpoint (SQL Server)
aws dms create-endpoint \
  --endpoint-identifier legacy-sqlserver-src \
  --endpoint-type source \
  --engine-name sqlserver \
  --username sql_user \
  --password 'REPLACE_WITH_PASSWORD' \
  --server-name LEGACY_SQLSERVER_HOSTNAME_OR_IP \
  --port 1433 \
  --database-name LegacyHospitalDB

# 2. Create target endpoint (Postgres RDS)
aws dms create-endpoint \
  --endpoint-identifier rds-postgres-target \
  --endpoint-type target \
  --engine-name postgres \
  --username healthcare \
  --password 'REPLACE_WITH_PASSWORD' \
  --server-name YOUR_RDS_ENDPOINT \
  --port 5432 \
  --database-name healthcare \
  --ssl-mode require

# 3. Create replication task (you may need to first create a replication instance)
aws dms create-replication-task \
  --replication-task-identifier sqlserver-to-rds-healthcare \
  --source-endpoint-arn arn:aws:dms:REGION:ACCOUNT_ID:endpoint:SOURCE_ARN \
  --target-endpoint-arn arn:aws:dms:REGION:ACCOUNT_ID:endpoint:TARGET_ARN \
  --replication-instance-arn arn:aws:dms:REGION:ACCOUNT_ID:rep:REPLICATION_INSTANCE_ARN \
  --migration-type full-load-and-cdc \
  --table-mappings file://aws-dms/dms-task-settings.json \
  --replication-task-settings file://aws-dms/dms-task-settings.json

# 4. Start the replication task
aws dms start-replication-task \
  --replication-task-arn arn:aws:dms:REGION:ACCOUNT_ID:task:TASK_ARN \
  --start-replication-task-type start-replication
