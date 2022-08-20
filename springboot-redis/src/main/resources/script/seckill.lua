local userId=ARGV[1];
local kcKey=KEYS[1]
local sekUserKey=KEYS[2]

local alreadyExist=redis.call("SISMEMBER", sekUserKey, userId);
if tonumber(alreadyExist) == 1 then
return 2;
end

local kc=redis.call("GET", kcKey);
if not kc or tonumber(kc) <=0 then
return 0;
end

redis.call("DECR", kcKey);
redis.call("SADD", sekUserKey, userId);
return 1;