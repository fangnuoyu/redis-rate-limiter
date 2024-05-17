local key = KEYS[1]
local limit = tonumber(ARGV[1])
local window = tonumber(ARGV[2])

local current = redis.call('INCRBY', key, 1)
if current == 1 then
    redis.call('EXPIRE', key, window)
end

return tonumber(current)