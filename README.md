# Tournament-Planner
API-Beschreibung:

/api/games
gibt die aktuellen und vergangenen Spiele zurück

```json
[{"id": 1,
  "teamA":
     {"id":1,"name":"Team Solo Mid","players":[1,2],"creator":1},
   "teamB":
      {"id":2,"name":"Team Liquid",
        "players":[2],
        "creator":2},
   "startDate":1561097095541,
   "type":"LEAGUEOFLEGENDS",
   "status":"PENDING"}];
```
