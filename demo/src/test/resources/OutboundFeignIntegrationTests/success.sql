-- Delete previous data
truncate table demo.demo_audit_record restart identity;

-- Expected result
-- AuditRequest
insert into demo.demo_audit_record (create_date_time, error, http_status, transaction_id,
                                    provider, request_type,
                                    request_body,
                                    request_headers, request_method, response_body,
                                    response_headers,
                                    spend_time_in_ms, remote_address, url, request_id)
values ('2024-06-13 17:00', null, 200, 'transactionId4',
        'outbound-feign-provider', 'outbound-feign-request-type',
        '{"feignRequestTransactionId":"transactionId5"}',
        '{"Content-Type":["application/json"],"Content-Length":["46"]}',
        'POST',
        '{"feignResponseTransactionId":"feignResponseTransaction1"}',
        '{"matched-stub-id":["0602d6b3-589e-4658-ae93-b9ed723a0562"],"content-type":["application/json"],"transfer-encoding":["chunked"]}',
        333,
        null,
        '/feign', default);
