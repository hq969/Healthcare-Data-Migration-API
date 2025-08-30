INSERT INTO patients (id, first_name, last_name, date_of_birth, mrn, contact_number, email)
VALUES (1, 'Asha', 'Verma', '1990-01-01', 'MRN1001', '9999999999', 'asha@example.com')
ON CONFLICT (id) DO NOTHING;

INSERT INTO appointments (id, patient_id, scheduled_at, department, doctor, status)
VALUES (1, 1, '2025-09-01 10:00:00', 'Cardiology', 'Dr. Smith', 'SCHEDULED')
ON CONFLICT (id) DO NOTHING;

INSERT INTO billings (id, patient_id, amount, billed_at, status)
VALUES (1, 1, 1200.50, '2025-09-01 11:00:00', 'PENDING')
ON CONFLICT (id) DO NOTHING;
