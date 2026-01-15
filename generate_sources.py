import pandas as pd
import numpy as np
import random
import os
import logging
import sys
from datetime import datetime

# ==========================================
# 1. CONFIGURATION DU LOGGER
# ==========================================
# Création du dossier de logs si inexistant
log_dir = os.path.join(os.path.dirname(__file__), '../logs')
if not os.path.exists(log_dir):
    os.makedirs(log_dir)

log_filename = os.path.join(log_dir, f"etl_process_{datetime.now().strftime('%Y%m%d_%H%M%S')}.log")

# Configuration du format : [DATE HEURE] [NIVEAU] Message
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s [%(levelname)s] %(message)s',
    handlers=[
        logging.FileHandler(log_filename, encoding='utf-8'), # Ecrit dans un fichier
        logging.StreamHandler(sys.stdout)                   # Ecrit dans la console
    ]
)

logger = logging.getLogger(__name__)

logger.info("DÉMARRAGE DU SCRIPT DE GÉNÉRATION DE DONNÉES ...")

# ==========================================
# 2. CONFIGURATION DES CHEMINS
# ==========================================
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
INPUT_FILE = os.path.join(BASE_DIR, '../data/input_original/Raw_Data.xlsx')
OUTPUT_DIR = os.path.join(BASE_DIR, '../data/generated_sources')

# Vérification du dossier de sortie
if not os.path.exists(OUTPUT_DIR):
    logger.info(f"Création du dossier de sortie : {OUTPUT_DIR}")
    os.makedirs(OUTPUT_DIR)

# ==========================================
# 3. CHARGEMENT DES DONNÉES
# ==========================================
logger.info(f"Lecture du fichier source : {INPUT_FILE}")

if not os.path.exists(INPUT_FILE):
    logger.critical(f"❌ FICHIER NON TROUVÉ : {INPUT_FILE}")
    logger.critical("Veuillez placer le fichier 'Raw_Data.xlsx' dans le dossier 'data/input_original'.")
    sys.exit(1)

try:
    df = pd.read_excel(INPUT_FILE)
    logger.info(f"✅ Fichier chargé avec succès. {len(df)} lignes et {len(df.columns)} colonnes.")
except Exception as e:
    logger.error(f"❌ Erreur lors de la lecture du fichier Excel : {e}")
    sys.exit(1)

# ==========================================
# 4. GÉNÉRATION SOURCE A : ERP VENTES (CSV)
# ==========================================
try:
    logger.info("🔹 Traitement : Génération du fichier CSV (Ventes)...")
    csv_filename = os.path.join(OUTPUT_DIR, 'Source_ERP_Ventes.csv')
    
    # Export en CSV avec séparateur point-virgule (standard européen)
    df.to_csv(csv_filename, index=False, sep=';', encoding='utf-8')
    
    logger.info(f"✅ Export CSV terminé : {csv_filename}")
except Exception as e:
    logger.error(f"❌ Erreur export CSV : {e}")

# ==========================================
# 5. GÉNÉRATION SOURCE B : RH OBJECTIFS (EXCEL)
# ==========================================
try:
    logger.info("🔹 Traitement : Génération du fichier Excel (Objectifs RH)...")
    
    if 'Region' not in df.columns:
        logger.warning("Colonne 'Region' introuvable. Impossible de générer les objectifs par région.")
    else:
        unique_regions = df['Region'].unique()
        logger.info(f"Régions détectées : {len(unique_regions)}")

        # Liste de managers
        fake_managers = ['Alice Dupont', 'John Smith', 'Carlos Ruiz', 'Mei Lin', 'Amara Diop', 
                         'Hans Müller', 'Fatima Benali', 'Kenji Sato', 'Sarah Connor', 'James Bond', 
                         'Lara Croft', 'Bruce Wayne', 'Clark Kent']
        
        # Compléter si pas assez de noms
        while len(fake_managers) < len(unique_regions):
            fake_managers.append(f"Manager_{len(fake_managers)+1}")

        rh_data = []
        for i, region in enumerate(unique_regions):
            target = random.randint(10, 100) * 10000 
            manager = fake_managers[i]
            rh_data.append({'Region': region, 'Regional_Manager': manager, 'Yearly_Target': target})

        df_rh = pd.DataFrame(rh_data)
        xlsx_filename = os.path.join(OUTPUT_DIR, 'Source_RH_Targets.xlsx')
        df_rh.to_excel(xlsx_filename, index=False)
        
        logger.info(f"✅ Export Excel terminé : {xlsx_filename}")

except Exception as e:
    logger.error(f"❌ Erreur export Excel RH : {e}")

# ==========================================
# 6. GÉNÉRATION SOURCE C : LOGISTIQUE (XML)
# ==========================================
try:
    logger.info("🔹 Traitement : Génération du fichier XML (Retours Logistique)...")
    
    if 'Order ID' not in df.columns:
        logger.error("Colonne 'Order ID' manquante. Impossible de générer les retours.")
    else:
        all_orders = df['Order ID'].unique()
        # Simulation de 8% de retours
        num_returns = int(len(all_orders) * 0.08) 
        returned_orders = np.random.choice(all_orders, num_returns, replace=False)
        
        logger.info(f"Simulation de {num_returns} retours sur {len(all_orders)} commandes.")

        reasons = ['Defective', 'Wrong Item', 'Late Delivery', 'Damaged in Transit', 'Customer Changed Mind']
        
        xml_filename = os.path.join(OUTPUT_DIR, 'Source_Logistique_Retours.xml')

        # Construction du XML
        xml_content = ['<?xml version="1.0" encoding="UTF-8"?>', '<Returns>']
        for order_id in returned_orders:
            reason = random.choice(reasons)
            xml_content.append('  <Return>')
            xml_content.append(f"    <OrderID>{order_id}</OrderID>")
            xml_content.append(f"    <Status>Returned</Status>")
            xml_content.append(f"    <Reason>{reason}</Reason>")
            xml_content.append('  </Return>')
        xml_content.append('</Returns>')

        with open(xml_filename, 'w', encoding='utf-8') as f:
            f.write('\n'.join(xml_content))
            
        logger.info(f"✅ Export XML terminé : {xml_filename}")

except Exception as e:
    logger.error(f"❌ Erreur export XML : {e}")

logger.info("🏁 SCRIPT TERMINÉ AVEC SUCCÈS")
