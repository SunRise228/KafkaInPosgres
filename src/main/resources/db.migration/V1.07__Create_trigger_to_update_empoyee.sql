create or replace trigger "updbef_check"
before update on employee
for each row
execute procedure "trg_ans_updbef_check"();
