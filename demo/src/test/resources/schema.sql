create schema if not exists DEMO;

-- AuditRecord
create table if not exists DEMO.DEMO_AUDIT_RECORD (
    request_id serial NOT NULL PRIMARY KEY,

    provider varchar,
    request_type varchar,
    spend_time_in_ms int,
    request_headers text,
    request_body text,
    response_body text,
    response_headers text,
    error text,
    url varchar,
    http_status int,
    request_method varchar(10),
    create_date_time timestamp,
    remote_address varchar(255),
    transaction_id varchar
);

create index if not exists idx_demo_demo_audit_record_latest
    on demo.demo_audit_record (transaction_id, provider, request_type, http_status, create_date_time desc);
