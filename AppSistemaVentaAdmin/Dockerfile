# Etapa de construcción
FROM node:18 as build
WORKDIR /app
COPY package*.json ./
RUN npm install
RUN --mount=type=cache,target=/root/.npm npm ci
COPY . .
RUN npm run build --prod

# Etapa de ejecución
FROM nginx:alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/dist/app-sistema-venta /usr/share/nginx/html
EXPOSE 80
ENTRYPOINT ["nginx", "-c", "/etc/nginx/nginx.conf"]
CMD ["-g", "daemon off;"]
