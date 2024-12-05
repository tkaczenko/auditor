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
values ('2024-06-13 17:00', null, 200, 'transactionId2', 'trace_id',
        'outbound-rest-template-provider', 'outbound-rest-template-request-type',
        null,
        '{"Authorization":["Basic Y2xpZW50SWQ6Y2xpZW50U2VjcmV0"],"Accept":["application/json, application/*+json"],"Content-Length":["0"]}',
        'GET',
        '{"restTemplateResponseTransactionId":"restTemplateResponseTransaction1"}',
        '{"Content-Type":["application/json"],"Matched-Stub-Id":["847eb090-6df5-409b-904a-d494504d3af7"],"Transfer-Encoding":["chunked"],"Vary":["Accept-Encoding, User-Agent"]}',
        333,
        null,
        '/demo-client', default);
