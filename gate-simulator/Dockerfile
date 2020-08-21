FROM node:8.16.2-alpine
WORKDIR /opt/app
COPY . .
RUN npm install
COPY . .
CMD ["npm", "start"]
EXPOSE 9999