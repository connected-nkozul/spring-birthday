FROM gitpod/workspace-mysql
ENV NGINX_DOCROOT_IN_REPO="src/www"

# add your tools here ...
RUN sudo apt-get update
RUN sudo apt-get install -y mysql-client nano tmux dialog

RUN sudo echo -e "\n    _____                            _           _ \n   / ____|                          | |         | |\n  | |     ___  _ __  _ __   ___  ___| |_ ___  __| |\n  | |    / _ \\| '_ \\| '_ \\ / _ \\/ __| __/ _ \\/ _' |\n  | |___| (_) | | | | | | |  __/ (__| ||  __/ (_| |\n   \\_____\\___/|_| |_|_| |_|\\___|\\___|\\__\\___|\\__,_|\n"
RUN sudo sleep 4