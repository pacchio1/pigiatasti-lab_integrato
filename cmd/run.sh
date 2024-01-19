et absolute paths
APP_DIR="/app"
DB_DIR="/db"
PROJECT_DIR="$APP_DIR/backend/SeVedemo"

# Set MySQL credentials
MYSQL_USER="root"
MYSQL_PASSWORD="odiojava"
MYSQL_DB="sevedemo"

# Change permissions and ownership
chmod -R 771 "$APP_DIR"/*
chown -R $(whoami):$(whoami) "$APP_DIR"/*

# Check and start nginx
if ! service nginx status > /dev/null; then
	    service nginx start
fi

# Check and start mariadb
if ! service mariadb status > /dev/null; then
	    service mariadb start
fi

# Create MySQL user and database
mysql -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "CREATE USER 'backend'@'%' IDENTIFIED BY 'odiojava'"
mysql -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "GRANT ALL PRIVILEGES ON $MYSQL_DB.* TO 'backend'@'%'"
mysql -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "CREATE DATABASE IF NOT EXISTS $MYSQL_DB"
mysql -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" "$MYSQL_DB" < "$DB_DIR/sevedemo.sql"

# Start Spring Boot application
cd "$PROJECT_DIR"
export JAVA_HOME=/usr/lib/jvm/java-1.17.0-openjdk-amd64

# Build and run the application
./mvnw clean install
./mvnw spring-boot:run

