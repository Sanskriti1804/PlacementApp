import json
from pathlib import Path
from langchain_community.embeddings import HuggingFaceEmbeddings
from langchain_community.vectorstores import Chroma
import os
from dotenv import load_dotenv

load_dotenv()

# Configure Gemini API with new package
try:
    from google.genai import Client
    GEMINI_API_KEY = os.getenv("GEMINI_API_KEY")
    client = Client(api_key=GEMINI_API_KEY)
except ImportError:
    client = None

class PlacementChatbot:
    def __init__(self):
        """Initialize the RAG chatbot"""
        print("🚀 Initializing Placement Chatbot...")
        
        # Load embeddings
        self.embeddings = HuggingFaceEmbeddings(
            model_name="sentence-transformers/all-MiniLM-L6-v2"
        )
        
        # Load vector store
        db_path = Path("knowledge_base/chroma_db")
        self.vector_store = Chroma(
            persist_directory=str(db_path),
            embedding_function=self.embeddings
        )
        
        # Create retriever
        self.retriever = self.vector_store.as_retriever(
            search_kwargs={"k": 3}
        )
        
        self.client = client
        print("✓ Chatbot initialized successfully!")
    
    def query_knowledge_base(self, question):
        """Query local knowledge base"""
        try:
            retrieved_docs = self.retriever.invoke(question)
            context = "\n\n".join([doc.page_content for doc in retrieved_docs])
            return context
        except:
            return ""
    
    def query_gemini(self, question, context=""):
        """Use Gemini API for answers"""
        if not self.client:
            return "Gemini API not configured"
        
        if context:
            prompt = f"""You are a placement assistant. Answer based on:

{context}

Q: {question}
A:"""
        else:
            prompt = f"Q: {question}\nA:"
        
        try:
            response = client.models.generate_content(
                model="gemini-2.0-flash",
                contents=prompt,
            )
            return response.text
        except Exception as e:
            return f"Error: {str(e)}"
    
    def query(self, question):
        """Query the chatbot"""
        context = self.query_knowledge_base(question)
        
        if len(context) > 100:
            answer = self.query_gemini(question, context)
            source = "Knowledge Base + Gemini"
        else:
            answer = self.query_gemini(question, "")
            source = "Gemini AI"
        
        return {
            "question": question,
            "answer": answer,
            "source": source
        }

def main():
    chatbot = PlacementChatbot()
    
    queries = [
        "What is TCS CGPA requirement?",
        "How do I prepare for interviews?"
    ]
    
    for q in queries:
        print(f"\nQ: {q}")
        result = chatbot.query(q)
        print(f"A: {result['answer'][:200]}...")

if __name__ == "__main__":
    main()