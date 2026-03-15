# Placement Chatbot

AI-powered chatbot for JIMS Rohini placement information using RAG.

## Setup
```bash
python -m venv venv
venv\Scripts\activate
pip install -r requirements.txt
```

## Run
```bash
python app.py
```

API runs at: `http://localhost:5000`

## Features
- RAG (Retrieval Augmented Generation)
- Vector embeddings with ChromaDB
- Web scraping for placement data
- Flask API deployment