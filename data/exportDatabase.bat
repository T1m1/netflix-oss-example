mongoexport --host localhost --port 1001 --db test --collection user --out user.json
mongoexport --host localhost --port 1002 --db test --collection message --out message.json
mongoexport --host localhost --port 1003 --db test --collection document --out document.json