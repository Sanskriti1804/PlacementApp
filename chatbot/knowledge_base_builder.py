import json
from pathlib import Path
from langchain_text_splitters import RecursiveCharacterTextSplitter
from langchain_community.embeddings import HuggingFaceEmbeddings
from langchain_community.vectorstores import Chroma
import os

def prepare_documents(cleaned_data):
    """Convert cleaned data to text documents"""
    documents = []
    
    for record in cleaned_data:
        # Create readable text from structured data
        text = f"""
Company: {record.get('company_name', '')}
Sector: {record.get('sector', '')}

Eligibility:
- CGPA Required: {record.get('cgpa_requirement', 'Not specified')}
- Eligible Branches: {', '.join(record.get('eligible_branches', []))}
- Backlogs: {record.get('backlogs_allowed', 'Not specified')}

Compensation:
- CTC: {record.get('ctc', '')}
- Stipend: {record.get('stipend', '')}

Drive Timeline:
- Date: {record.get('drive_date', 'Not announced')}
- Deadline: {record.get('application_deadline', 'Not specified')}

Selection Process:
{', '.join(record.get('selection_process', []))}

Internship:
- Duration: {record.get('internship_duration', '')}
- Location: {record.get('internship_location', 'Not specified')}

Statistics: {json.dumps(record.get('placement_stats', {}), indent=2)}
        """
        documents.append(text.strip())
    
    return documents

def create_knowledge_base():
    """
    Phase 3: Knowledge Base Creation
    
    Sub-step 1: Chunking - Break documents into 300-500 word pieces
    Sub-step 2: Embedding - Convert to vectors using sentence-transformers
    Sub-step 3: Storage - Store in ChromaDB for fast searching
    """
    
    # Load cleaned data
    cleaned_file = Path("data/cleaned/placement_data_cleaned.json")
    with open(cleaned_file, 'r') as f:
        cleaned_data = json.load(f)
    
    print(f"📚 Loaded {len(cleaned_data)} cleaned records")
    
    # SUB-STEP 1: Chunking
    print("\n🔀 Sub-step 1: Chunking documents...")
    documents = prepare_documents(cleaned_data)
    
    splitter = RecursiveCharacterTextSplitter(
        chunk_size=500,
        chunk_overlap=50,
        separators=["\n\n", "\n", " ", ""]
    )
    
    chunks = []
    for doc in documents:
        doc_chunks = splitter.split_text(doc)
        chunks.extend(doc_chunks)
    
    print(f"✓ Created {len(chunks)} text chunks")
    
    # SUB-STEP 2: Embedding
    print("\n🧠 Sub-step 2: Creating embeddings...")
    embeddings = HuggingFaceEmbeddings(
        model_name="sentence-transformers/all-MiniLM-L6-v2"
    )
    print("✓ Embeddings model loaded")
    
    # SUB-STEP 3: Storage in ChromaDB
    print("\n💾 Sub-step 3: Storing in ChromaDB...")
    
    db_path = Path("knowledge_base/chroma_db")
    db_path.mkdir(parents=True, exist_ok=True)
    
    # Create vector store
    vector_store = Chroma.from_texts(
        texts=chunks,
        embedding=embeddings,
        persist_directory=str(db_path)
    )
    vector_store.persist()
    
    print(f"✓ Knowledge base created at: {db_path}")
    print(f"✓ Total vectors stored: {len(chunks)}")
    
    return vector_store

if __name__ == "__main__":
    create_knowledge_base()
    print("\n✅ Knowledge Base Creation Complete!")
