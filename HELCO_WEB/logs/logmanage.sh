find /srm/HELCO_WEB/weeklyLogs -type f -mtime 7 -exec rm {} \;
find /srm/HELCO_WEB/logs -type f -mtime 70 -exec rm {} \;
find /srm/HELCO_WEB/logs -type f -mtime -1 -exec cp -rfp {} /srm/HELCO_WEB/weeklyLogs/ \;

