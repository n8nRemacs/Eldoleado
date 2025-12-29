FROM python:3.11-slim

WORKDIR /app

# Install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy application
COPY . .

# Create sessions directory
RUN mkdir -p /app/sessions

# Expose port
EXPOSE 3002

# Run
CMD ["python", "app.py"]
