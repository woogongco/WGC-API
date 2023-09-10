docker rm -f $(docker ps -q  -f "name=server")

docker system prune -f

docker build --tag app .

docker run -d --name server -p 80:8080 --restart=always  app:latest