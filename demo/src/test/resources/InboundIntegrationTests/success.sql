-- Delete previous data
truncate table demo.demo_audit_record restart identity;

-- Expected result
-- AuditRequest
insert into demo.demo_audit_record (create_date_time, error, http_status, transaction_id, trace_id,
                                    provider, request_type,
                                    request_body,
                                    request_headers, request_method, response_body,
                                    response_headers,
                                    spend_time_in_ms, remote_address, url, request_id)
values ('2024-06-13 17:00', null, 200, 'transactionId0', 'trace_id',
        'inbound-provider', 'inbound-requestType',
        '{"requestTransactionId":"transactionId1"}',
        '{"content-length":["50"],"host":["localhost:55060"],"content-type":["application/json"],"connection":["Keep-Alive"],"accept-encoding":["gzip,deflate"],"accept":["*/*"],"user-agent":["Apache-HttpClient/4.5.13 (Java/21.0.2)"]}',
        'POST',
        '{"responseTransactionId":"transactionId1"}',
        '{"Content-Type":["application/json"]}',
        333,
        '127.0.0.1',
        '/test/inbound', default);
