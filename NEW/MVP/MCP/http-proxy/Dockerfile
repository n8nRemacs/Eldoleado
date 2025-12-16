FROM python:3.11-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Optional: install playwright for /render
# RUN pip install playwright && playwright install chromium --with-deps

COPY . .

EXPOSE 3010

CMD ["python", "app.py"]
