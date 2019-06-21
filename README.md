# Tournament-Planner
API-Beschreibung:

<h3>/api/games</h3> <br>
Gibt die aktuellen und vergangenen Spiele zurück<br>
Optional kann der Parameter id mitgegeben werden. (Filtert nach Game mit Id) <br>
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
Optional kann der Parameter id mitgegeben werden. (Filtert nach Game mit Id)
Gibt die aktuell vorhanden Teams zurück
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

<h3>/api/players</h3> <br>
Gibt die aktuell vorhandenen Spieler zurück 
Optional kann der Parameter id mitgegeben werden. (Filtert nach Game mit Id)
```json
[{
  "id":1,
  "name":"oberstrike",
  "teams":
    [{
    "id":1,
    "name":"Team Solo Mid",
    "players":[2,1],
    "creator":1
    }]
}]
```

