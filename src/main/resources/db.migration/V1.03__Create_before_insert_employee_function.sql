create or replace function public.trg_ans_insbef_check()
returns trigger as
$body$
begin
if new.role = 'OWNER' and
(select count(*) from employee e
where e.company_id = new.company_id and e.role = 'OWNER') = 1
then
raise exception  'Owner of the company already exist';
end if;
return new;
end;
$body$
language plpgsql volatile
cost 100;