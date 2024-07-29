CREATE TABLE public.conta
(
    id uuid NOT NULL,
    data_vencimento date,
    data_pagamento date,
    valor double precision,
    descricao character varying(500),
    situacao character varying(100),
    CONSTRAINT conta_pkey PRIMARY KEY (id)
)