package com.healthcare.service;

import com.healthcare.dynamodb.AuditLog;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.time.Instant;

@Service
public class AuditService {
    private final DynamoDbTable<AuditLog> auditLogTable;

    public AuditService(DynamoDbTable<AuditLog> auditLogTable) {
        this.auditLogTable = auditLogTable;
    }

    public void record(String entityType, String entityId, String action, String payloadJson) {
        AuditLog log = new AuditLog();
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setAction(action);
        log.setOccurredAt(Instant.now());
        log.setPayloadJson(payloadJson);
        auditLogTable.putItem(log);
    }
}
