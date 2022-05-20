drop trigger insert_scheduler_service;
-- DELIMITER $$
-- create trigger insert_scheduler_service after insert ON orders 
-- for each row
-- begin
-- 	DROP TEMPORARY TABLE IF EXISTS tmpTable_insert_scheduler_service;
--     CREATE TEMPORARY TABLE IF NOT EXISTS tmpTable_insert_scheduler_service select
--     servicepackage_service.service_id, orders.startTime, orders.endTime, orders.username
--     as newServiceId, newStartTime, newEndTime, newUsername from servicepackage_service join period on servicepackage_service.servicepackage_id = period.packageId
--     join orders on orders.periodId = period.ID where orders.orderState = "Paid" and orders.orderId = new.orderId;
--  	
-- --     for each row in tempTable_insert_scheduler_service
-- 	insert into activation_scheduler_service(serviceId,startTime,endTime,username) values 
--     (tmpTable_insert_scheduler_service.newServiceId,
--     tmpTable_insert_scheduler_service.newStartTime,
--     tmpTable_insert_scheduler_service.newEndTime,
--     tmpTable_insert_scheduler_service.newUsername);
--     DROP TEMPORARY TABLE tmpTable_insert_scheduler_service;
-- end$$

-- DELIMITER ;

DELIMITER $$
create trigger insert_scheduler_service after insert ON orders 
for each row
begin

	insert into activation_scheduler_service(serviceId,startTime,endTime,username) select
    servicepackage_service.service_id, orders.startTime, orders.endTime, orders.username
	from servicepackage_service join period on servicepackage_service.servicepackage_id = period.packageId
    join orders on orders.periodId = period.ID where orders.orderState = "Paid" and orders.orderId = new.orderId;
end$$

DELIMITER ;
