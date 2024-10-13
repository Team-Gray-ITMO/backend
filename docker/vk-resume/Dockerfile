FROM php:8.2-cli

RUN apt-get update  \
    && apt-get install -y \
    libzip-dev \
    git \
    && docker-php-ext-install zip

WORKDIR /var/www/html

COPY ../.. ./

RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer

RUN composer install --no-interaction --optimize-autoloader

RUN chown -R www-data:www-data /var/www/html/storage /var/www/html/bootstrap/cache

ENTRYPOINT php artisan serve --host=0.0.0.0 --port=8000
