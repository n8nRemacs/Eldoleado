FROM node:20-alpine

# Install build dependencies for native modules
RUN apk add --no-cache python3 make g++ git

WORKDIR /app

# Copy package files
COPY package*.json ./
COPY tsconfig.json ./

# Install dependencies
RUN npm install

# Copy source code
COPY src/ ./src/

# Build TypeScript
RUN npm run build

# Create sessions directory
RUN mkdir -p /app/sessions

# Expose port
EXPOSE 8766

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8766/health || exit 1

# Run
CMD ["node", "dist/index.js"]
