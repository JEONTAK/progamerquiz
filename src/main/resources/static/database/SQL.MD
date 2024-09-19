SELECT
p.progamer_id AS progamer_id,
p.pid AS progamer_pid,
p.name AS progamer_name,
p.birth AS progamer_birth,
p.position AS progamer_position,
p.league_win AS progamer_league_win,
p.intl_win AS progamer_intl_win,
p.nationality AS progamer_nationality,
t.team_id AS team_id,
t.name AS team_name,
t.season_year AS team_year,
t.league AS team_league
FROM
progamer p
JOIN
progamer_team pt ON p.progamer_id = pt.progamer_id
JOIN
team t ON pt.team_id = t.team_id
ORDER BY
p.progamer_id;

//전체 조회 쿼리
