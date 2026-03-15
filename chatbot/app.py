from flask import Flask, request, jsonify
from flask_cors import CORS
from chatbot import PlacementChatbot
import logging
from pathlib import Path

# Setup logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Initialize Flask app
app = Flask(__name__)
CORS(app)

# Initialize chatbot (loads once at startup)
try:
    chatbot = PlacementChatbot()
    logger.info("✓ Chatbot initialized")
except Exception as e:
    logger.error(f"Failed to initialize chatbot: {e}")
    chatbot = None

# Routes
@app.route('/', methods=['GET'])
def home():
    """Health check endpoint"""
    return jsonify({
        "status": "online",
        "message": "Placement Chatbot API is running",
        "endpoints": {
            "POST /query": "Send a question to the chatbot",
            "GET /health": "Health check",
            "POST /batch": "Send multiple queries at once"
        }
    })

@app.route('/health', methods=['GET'])
def health():
    """Health check"""
    return jsonify({
        "status": "ok",
        "chatbot": "initialized" if chatbot else "error"
    }), 200

@app.route('/query', methods=['POST'])
def query():
    """
    Query the chatbot
    
    Request JSON:
    {
        "question": "What is TCS CGPA requirement?"
    }
    
    Response:
    {
        "question": "...",
        "answer": "...",
        "sources": [...]
    }
    """
    
    if not chatbot:
        return jsonify({"error": "Chatbot not initialized"}), 500
    
    try:
        data = request.get_json()
        question = data.get('question', '').strip()
        
        if not question:
            return jsonify({"error": "Question cannot be empty"}), 400
        
        result = chatbot.query(question)
        
        return jsonify(result), 200
    
    except Exception as e:
        logger.error(f"Error processing query: {e}")
        return jsonify({"error": str(e)}), 500

@app.route('/batch', methods=['POST'])
def batch_query():
    """
    Process multiple queries at once
    
    Request JSON:
    {
        "questions": ["Question 1", "Question 2"]
    }
    """
    
    if not chatbot:
        return jsonify({"error": "Chatbot not initialized"}), 500
    
    try:
        data = request.get_json()
        questions = data.get('questions', [])
        
        if not questions:
            return jsonify({"error": "Questions list cannot be empty"}), 400
        
        results = []
        for question in questions:
            result = chatbot.query(question)
            results.append(result)
        
        return jsonify({"results": results}), 200
    
    except Exception as e:
        logger.error(f"Error in batch processing: {e}")
        return jsonify({"error": str(e)}), 500

@app.errorhandler(404)
def not_found(error):
    return jsonify({"error": "Endpoint not found"}), 404

@app.errorhandler(500)
def internal_error(error):
    return jsonify({"error": "Internal server error"}), 500

if __name__ == '__main__':
    # Run on localhost:5000
    # For production, use a production WSGI server like Gunicorn
    app.run(
        host='0.0.0.0',
        port=5000,
        debug=True
    )
