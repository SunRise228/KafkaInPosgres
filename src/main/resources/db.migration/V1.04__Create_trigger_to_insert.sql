create or replace trigger "insbef_check"
before insert on employee
for each row
execute procedure "trg_ans_insbef_check"();
