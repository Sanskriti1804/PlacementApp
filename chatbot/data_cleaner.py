import json
import pandas as pd
from pathlib import Path
import re

def clean_text(text):
    """Remove unwanted characters and normalize text"""
    if not isinstance(text, str):
        return text
    
    # Remove HTML tags
    text = re.sub(r'<[^>]+>', '', text)
    # Remove extra whitespace
    text = re.sub(r'\s+', ' ', text).strip()
    return text

def validate_and_clean(data):
    """
    Validate and clean placement data
    
    KEEP:
    - Company names and sectors
    - Eligibility criteria (CGPA, branches)
    - CTC and stipend details
    - Drive dates and deadlines
    - Selection process rounds
    - Internship details
    - Year-wise placement statistics
    
    REMOVE:
    - Navigation menus, headers, footers
    - Advertisements, social media links
    - Repeated boilerplate text
    - HTML tags and formatting
    """
    
    cleaned_data = []
    
    for record in data:
        cleaned_record = {
            # Keep: Company info
            "company_name": clean_text(record.get("company_name", "")),
            "sector": clean_text(record.get("sector", "")),
            
            # Keep: Eligibility criteria
            "cgpa_requirement": record.get("cgpa_requirement"),
            "eligible_branches": record.get("eligible_branches", []),
            "backlogs_allowed": record.get("backlogs_allowed", "Not specified"),
            
            # Keep: CTC and stipend
            "ctc": clean_text(record.get("ctc", "")),
            "stipend": clean_text(record.get("stipend", "")),
            
            # Keep: Drive dates and deadlines
            "drive_date": record.get("drive_date", ""),
            "application_deadline": record.get("application_deadline", ""),
            
            # Keep: Selection process
            "selection_process": record.get("selection_process", []),
            
            # Keep: Internship details
            "internship_duration": record.get("internship_duration", ""),
            "internship_location": record.get("internship_location", ""),
            
            # Keep: Placement statistics
            "placement_stats": record.get("placement_stats", {})
        }
        
        # Remove None values
        cleaned_record = {k: v for k, v in cleaned_record.items() if v}
        cleaned_data.append(cleaned_record)
    
    return cleaned_data

def save_cleaned_data(cleaned_data):
    """Save cleaned data to JSON"""
    cleaned_dir = Path("data/cleaned")
    cleaned_dir.mkdir(parents=True, exist_ok=True)
    
    output_file = cleaned_dir / "placement_data_cleaned.json"
    
    with open(output_file, 'w') as f:
        json.dump(cleaned_data, f, indent=2)
    
    print(f"✓ Cleaned data saved: {output_file}")
    return output_file

def main():
    # Load raw data
    raw_file = Path("data/raw/placement_data.json")
    
    with open(raw_file, 'r') as f:
        raw_data = json.load(f)
    
    print(f"📥 Loaded {len(raw_data)} raw records")
    
    # Clean data
    cleaned_data = validate_and_clean(raw_data)
    
    print(f"🧹 Cleaned {len(cleaned_data)} records")
    
    # Save cleaned data
    save_cleaned_data(cleaned_data)
    
    # Show sample
    print("\n📊 Sample cleaned record:")
    print(json.dumps(cleaned_data[0], indent=2))

if __name__ == "__main__":
    main()