app:
	mvn compile jib:dockerBuild
	docker compose up -d
app_down:
	docker compose down