-- Patients
CREATE TABLE IF NOT EXISTS patients (
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR(150) NOT NULL,
  last_name VARCHAR(150) NOT NULL,
  date_of_birth DATE,
  mrn VARCHAR(100) NOT NULL UNIQUE,
  contact_number VARCHAR(30),
  email VARCHAR(255)
);

-- Appointments
CREATE TABLE IF NOT EXISTS appointments (
  id BIGSERIAL PRIMARY KEY,
  patient_id BIGINT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
  scheduled_at TIMESTAMP NOT NULL,
  department VARCHAR(150) NOT NULL,
  doctor VARCHAR(150),
  status VARCHAR(50) NOT NULL
);

-- Billings
CREATE TABLE IF NOT EXISTS billings (
  id BIGSERIAL PRIMARY KEY,
  patient_id BIGINT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
  amount NUMERIC(12,2) NOT NULL,
  billed_at TIMESTAMP NOT NULL,
  status VARCHAR(50) NOT NULL
);

-- Indexes
CREATE INDEX IF NOT EXISTS idx_patients_mrn ON patients(mrn);
CREATE INDEX IF NOT EXISTS idx_appointments_patient ON appointments(patient_id);
CREATE INDEX IF NOT EXISTS idx_billings_patient ON billings(patient_id);
