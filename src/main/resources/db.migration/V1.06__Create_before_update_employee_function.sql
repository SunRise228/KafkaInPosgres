create or replace function public.trg_ans_updbef_check()
returns trigger as
$body$
begin
new.last_update_time = current_date;
return new;
end;
$body$
language plpgsql volatile
cost 100;