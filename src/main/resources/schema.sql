CREATE TABLE IF NOT EXISTS member (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(50)  NOT NULL,
    email      VARCHAR(100),
    phone      VARCHAR(20),
    birth_date DATE,
    address    VARCHAR(200),
    status     VARCHAR(10)  DEFAULT 'ACTIVE',
    created_at DATETIME     DEFAULT NOW(),
    updated_at DATETIME     DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS plan (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    plan_name   VARCHAR(50)  NOT NULL,
    monthly_fee INT          NOT NULL,
    data_gb     INT,
    call_min    INT,
    description VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS subscription (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT  NOT NULL,
    plan_id    BIGINT  NOT NULL,
    start_date DATE    NOT NULL,
    end_date   DATE,
    status     VARCHAR(10) DEFAULT 'ACTIVE',
    created_at DATETIME    DEFAULT NOW(),
    FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE,
    FOREIGN KEY (plan_id)   REFERENCES plan(id)
);
