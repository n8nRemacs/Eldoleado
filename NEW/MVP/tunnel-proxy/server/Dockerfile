FROM python:3.11-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY tunnel_client.py .

ENV PROXY_PORT=8080
ENV MOBILE_WS_URL=ws://host.docker.internal:8765/ws
ENV TUNNEL_SECRET=change_me

EXPOSE 8080

CMD ["python", "tunnel_client.py"]
