# Tournament-Planner
API-Beschreibung:

<h3>/api/games</h3> <br>
gibt die aktuellen und vergangenen Spiele zurück

```json
[{
  "id": 1,
  "teamA":
     {"id":1,
     "name":"Team Solo Mid",
     "players":
        [1,
        2],
     "creator":1
     },
  "teamB":
      {"id":2,
      "name":"Team Liquid",
      "players":[2],
      "creator":2
      },
   "startDate":1561097095541,
   "type":"LEAGUEOFLEGENDS",
   "status":"PENDING"
}];
```
<h3>/api/teams</h3> <br>

gibt die aktuell vorhanden Teams zurück
```json
[{
  "id":1,
  "name":"Team Solo Mid",
  "players":
     [2,
      1],
  "creator":1
}]
```
