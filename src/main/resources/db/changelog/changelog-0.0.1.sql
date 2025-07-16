CREATE TABLE login_user
(
    id        UUID primary key,
    user_name VARCHAR(100),
    password  VARCHAR(100),
    role      VARCHAR(30)
) INHERITS (public.audit);