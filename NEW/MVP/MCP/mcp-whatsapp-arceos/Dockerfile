FROM node:20-alpine

WORKDIR /app

# Install dependencies for canvas/sharp if needed
RUN apk add --no-cache python3 make g++ cairo-dev pango-dev jpeg-dev giflib-dev git openssh-client

COPY package*.json ./
RUN npm install

COPY tsconfig.json ./
COPY src ./src

RUN npm run build

# Create sessions directory
RUN mkdir -p /app/sessions

ENV PORT=8769
ENV SESSIONS_DIR=/app/sessions

EXPOSE 8769

CMD ["node", "dist/index.js"]
