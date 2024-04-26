docker run \
--name zad1 \
--privileged  \
--mount type=bind,source=./htdocs,target=/usr/local/apache2/htdocs  \
-p 8081:80 \
-d \
toskovski/zad1
