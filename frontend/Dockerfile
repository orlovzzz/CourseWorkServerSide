FROM node:latest

COPY frontend/public .
COPY frontend/src .
COPY frontend/package.json .
RUN npm install
CMD ["npm", "start"]