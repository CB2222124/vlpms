FROM node
WORKDIR /vlpms
COPY package*.json .
RUN npm install
COPY . .
CMD ["npm", "run", "dev"]