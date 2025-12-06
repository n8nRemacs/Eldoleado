FROM python:3.11-slim

WORKDIR /app

# Install system dependencies
RUN apt-get update && apt-get install -y \
    gcc \
    libpq-dev \
    && rm -rf /var/lib/apt/lists/*

# Copy requirements and install
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy application
COPY . .

# Create docs directory (will be mounted)
RUN mkdir -p /app/docs

# Expose port
EXPOSE 8090

# Run server
CMD ["uvicorn", "app:app", "--host", "0.0.0.0", "--port", "8090"]
